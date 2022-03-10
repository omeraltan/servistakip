import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class TabloTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.tabloTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  gercekVeritabaniInput: ElementFinder = element(by.css('input#tablo-temel-gercekVeritabani'));
  gercekLogTabloInput: ElementFinder = element(by.css('input#tablo-temel-gercekLogTablo'));
  gercekaciklamasiInput: ElementFinder = element(by.css('input#tablo-temel-gercekaciklamasi'));
  testVeritabaniInput: ElementFinder = element(by.css('input#tablo-temel-testVeritabani'));
  testLogTabloInput: ElementFinder = element(by.css('input#tablo-temel-testLogTablo'));
  testaciklamasiInput: ElementFinder = element(by.css('input#tablo-temel-testaciklamasi'));
  servisSelect: ElementFinder = element(by.css('select#tablo-temel-servis'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setGercekVeritabaniInput(gercekVeritabani) {
    await this.gercekVeritabaniInput.sendKeys(gercekVeritabani);
  }

  async getGercekVeritabaniInput() {
    return this.gercekVeritabaniInput.getAttribute('value');
  }

  async setGercekLogTabloInput(gercekLogTablo) {
    await this.gercekLogTabloInput.sendKeys(gercekLogTablo);
  }

  async getGercekLogTabloInput() {
    return this.gercekLogTabloInput.getAttribute('value');
  }

  async setGercekaciklamasiInput(gercekaciklamasi) {
    await this.gercekaciklamasiInput.sendKeys(gercekaciklamasi);
  }

  async getGercekaciklamasiInput() {
    return this.gercekaciklamasiInput.getAttribute('value');
  }

  async setTestVeritabaniInput(testVeritabani) {
    await this.testVeritabaniInput.sendKeys(testVeritabani);
  }

  async getTestVeritabaniInput() {
    return this.testVeritabaniInput.getAttribute('value');
  }

  async setTestLogTabloInput(testLogTablo) {
    await this.testLogTabloInput.sendKeys(testLogTablo);
  }

  async getTestLogTabloInput() {
    return this.testLogTabloInput.getAttribute('value');
  }

  async setTestaciklamasiInput(testaciklamasi) {
    await this.testaciklamasiInput.sendKeys(testaciklamasi);
  }

  async getTestaciklamasiInput() {
    return this.testaciklamasiInput.getAttribute('value');
  }

  async servisSelectLastOption() {
    await this.servisSelect.all(by.tagName('option')).last().click();
  }

  async servisSelectOption(option) {
    await this.servisSelect.sendKeys(option);
  }

  getServisSelect() {
    return this.servisSelect;
  }

  async getServisSelectedOption() {
    return this.servisSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setGercekVeritabaniInput('gercekVeritabani');
    await waitUntilDisplayed(this.saveButton);
    await this.setGercekLogTabloInput('gercekLogTablo');
    await waitUntilDisplayed(this.saveButton);
    await this.setGercekaciklamasiInput('gercekaciklamasi');
    await waitUntilDisplayed(this.saveButton);
    await this.setTestVeritabaniInput('testVeritabani');
    await waitUntilDisplayed(this.saveButton);
    await this.setTestLogTabloInput('testLogTablo');
    await waitUntilDisplayed(this.saveButton);
    await this.setTestaciklamasiInput('testaciklamasi');
    await this.servisSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
