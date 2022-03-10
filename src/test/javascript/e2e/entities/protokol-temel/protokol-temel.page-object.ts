import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ProtokolTemelUpdatePage from './protokol-temel-update.page-object';

const expect = chai.expect;
export class ProtokolTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.protokolTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-protokolTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ProtokolTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('protokol-temel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('protokol-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateProtokolTemel() {
    await this.createButton.click();
    return new ProtokolTemelUpdatePage();
  }

  async deleteProtokolTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const protokolTemelDeleteDialog = new ProtokolTemelDeleteDialog();
    await waitUntilDisplayed(protokolTemelDeleteDialog.deleteModal);
    expect(await protokolTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.protokolTemel.delete.question/);
    await protokolTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(protokolTemelDeleteDialog.deleteModal);

    expect(await isVisible(protokolTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
